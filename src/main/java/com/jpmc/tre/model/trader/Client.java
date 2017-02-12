package com.jpmc.tre.model.trader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;

import com.jpmc.tre.exception.ApplicationException;
import com.jpmc.tre.logging.Logger;
import com.jpmc.tre.model.Entity;
import com.jpmc.tre.model.Instruction;
import com.jpmc.tre.model.Instruction.InstructionBuilder;
import com.jpmc.tre.model.Side;
import com.jpmc.tre.service.BusinessFacade;
import com.jpmc.tre.service.CurrencyService;
import com.jpmc.tre.service.Facade;
import com.jpmc.tre.util.PropertyService;

public class Client {

	private final String SEPERATOR = ",";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yy");

	private Facade facade;
	private CurrencyService currencyService;

	public Client() {
		facade = BusinessFacade.getInstance();
		currencyService = CurrencyService.getInstance();
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.order();
		client.viewOrders();
		client.report();
	}

	private void report() {
		facade.report();
	}

	private void viewOrders() {
		facade.display();
	}

	private void order() {
		Collection<Instruction> instructions = loadInstructions(
				PropertyService.getInstance().getProperty("instruction.input.file"));
		facade.send(instructions);
	}

	private Collection<Instruction> loadInstructions(String fileName) {
		Collection<Instruction> instructions = new HashSet<>();
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
			Collection<String> lines = Files.readAllLines(path);
			for (String line : lines) {
				try {
					String[] rawData = line.split(SEPERATOR);
					Instruction instruction = createInstruction(rawData);
					instructions.add(instruction);
				} catch (Exception exception) {
					Logger.warn("Invalid data received in input file. " + line, exception);
				}
			}
		} catch (FileNotFoundException e) {
			Logger.error(String.format("Input data %s file is missing.", fileName));
		} catch (IOException e) {
			Logger.error(String.format("Input data file is missing: %s", fileName));
		} catch (URISyntaxException e) {
			Logger.error(String.format("Invalid file path: %s", fileName));
		}
		return instructions;
	}

	private Instruction createInstruction(String[] rawData) throws NumberFormatException, ApplicationException {
		InstructionBuilder builder = new InstructionBuilder().entity(new Entity(rawData[Index.ENTITY.position]))
				.side(Side.valueOf(rawData[Index.SIDE.position]))
				.agreedFx(Double.parseDouble(rawData[Index.AGREED_FX.position]))
				.currency(currencyService.getCurrency(rawData[Index.CURRENCY.position]))
				.instructionDate(LocalDate.parse(rawData[Index.INSTRUCTION_DATE.position], formatter))
				.settlementDate(LocalDate.parse(rawData[Index.SETTLEMENT_DATE.position], formatter))
				.units(Integer.parseInt(rawData[Index.UNITS.position]))
				.price(Double.parseDouble((rawData[Index.PRICE.position])));
		return builder.build();
	}

}
