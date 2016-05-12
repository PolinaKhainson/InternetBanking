package ua.nure.khainson.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.<br/>
 * 
 * @author P.Khainson
 * 
 */
public class CommandContainer {

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("registrateUser", new RegistrateUserCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("updateSettings", new UpdateSettingsCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("changeLocale", new ChangeLocaleCommand());

		// client commands
		commands.put("listUserPayments", new ListUserPaymentsCommand());
		commands.put("listUserAccounts", new ListUserAccountsCommand());
		commands.put("createPayment", new CreatePaymentCommand());
		commands.put("sendPayment", new SendPaymentCommand());
		commands.put("accountsForSelectedCardId",
				new ListAccountsForSelectedCreditCardCommand());
		commands.put("preparePayment", new PreparePaymentCommand());
		commands.put("confirmPayment", new ConfirmPaymentCommand());
		commands.put("confirmPreparedPayment",
				new ConfirmPreparedPaymentCommand());
		commands.put("createPdfReport", new CreatePdfReportCommand());
		commands.put("cancelPayment", new CancelPaymentCommand());
		commands.put("lockAccount", new LockAccountCommand());
		commands.put("unlockAccount", new UnlockAccountCommand());
		commands.put("addFunds", new AddFundsCommand());
		commands.put("sortPayments", new SortPaymentsCommand());
		commands.put("sortAccounts", new SortAccountsCommand());

		// admin commands
		commands.put("listUsers", new ListUsersCommand());
		commands.put("listAllPayments", new ListAllPaymentsCommand());
		commands.put("listAllAccounts", new ListAllAccountsCommand());
		commands.put("lockUser", new LockUserCommand());
		commands.put("unlockUser", new UnlockUserCommand());
		commands.put("deleteUser", new DeleteUserCommand());
		commands.put("createCreditCard", new CreateCreaditCardCommand());
		commands.put("createAccount", new CreateAccountCommand());
		commands.put("sendAccount", new SendAccountCommand());
		commands.put("sendCreditCard", new SendCreditCardCommand());
		commands.put("assignCreditCard", new AssignCreditCardCommand());
		commands.put("sortAccountsByOwner", new SortAccountsByOwnerCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}