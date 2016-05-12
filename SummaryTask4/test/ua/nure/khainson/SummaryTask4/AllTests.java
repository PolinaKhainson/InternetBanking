package ua.nure.khainson.SummaryTask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.nure.khainson.SummaryTask4.db.DBManagerTest;
import ua.nure.khainson.SummaryTask4.db.FieldsTest;
import ua.nure.khainson.SummaryTask4.db.LockStatusTest;
import ua.nure.khainson.SummaryTask4.db.PaymentStatusTest;
import ua.nure.khainson.SummaryTask4.db.RoleTest;
import ua.nure.khainson.SummaryTask4.db.bean.AccountCreditCardsBeanTest;
import ua.nure.khainson.SummaryTask4.db.bean.CreditCardAccountsBeanTest;
import ua.nure.khainson.SummaryTask4.db.bean.UserPaymentBeanTest;
import ua.nure.khainson.SummaryTask4.db.entity.AccountTest;
import ua.nure.khainson.SummaryTask4.db.entity.CreditCardTest;
import ua.nure.khainson.SummaryTask4.db.entity.EntityTest;
import ua.nure.khainson.SummaryTask4.db.entity.PaymentTest;
import ua.nure.khainson.SummaryTask4.db.entity.UserTest;
import ua.nure.khainson.SummaryTask4.exeption.AppExceptionTest;
import ua.nure.khainson.SummaryTask4.exeption.DBExceptionTest;
import ua.nure.khainson.SummaryTask4.exeption.MessagesTest;
import ua.nure.khainson.SummaryTask4.tag.GetLockStatusNameTest;
import ua.nure.khainson.SummaryTask4.web.ControllerTest;
import ua.nure.khainson.SummaryTask4.web.command.AddFundsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.CancelPaymentCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ChangeLocaleCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.CommandContainerTest;
import ua.nure.khainson.SummaryTask4.web.command.CommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ConfirmPaymentCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ConfirmPreparedPaymentCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.CreatePaymentCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.CreatePdfReportCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.DeleteUserCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ListAccountsForSelectedCreditCardCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ListAllAccountsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ListAllPaymentsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ListUserAccountsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ListUserPaymentsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ListUsersCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.LockAccountCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.LockUserCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.LoginCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.LogoutCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.NoCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.PreparePaymentCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.RegistrateUserCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.SendPaymentCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.SortAccountsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.SortPaymentsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.UnlockAccountCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.UnlockUserCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.UpdateSettingsCommandTest;
import ua.nure.khainson.SummaryTask4.web.command.ViewSettingsCommandTest;
import ua.nure.khainson.SummaryTask4.web.filter.CommandAccessFilterTest;
import ua.nure.khainson.SummaryTask4.web.filter.EncodingFilterTest;
import ua.nure.khainson.SummaryTask4.web.filter.NoCacheFilterTest;
import ua.nure.khainson.SummaryTask4.web.listener.ContextListenerTest;


@RunWith(Suite.class)
@SuiteClasses({AccountCreditCardsBeanTest.class, CreditCardAccountsBeanTest.class,
	UserPaymentBeanTest.class, AccountTest.class, CreditCardTest.class, 
	EntityTest.class, PaymentTest.class, UserTest.class, DBManagerTest.class,
	FieldsTest.class, LockStatusTest.class, PaymentStatusTest.class, RoleTest.class,
	AppExceptionTest.class, DBExceptionTest.class, MessagesTest.class,
	GetLockStatusNameTest.class, AddFundsCommandTest.class,
	CancelPaymentCommandTest.class, ChangeLocaleCommandTest.class,
	CommandTest.class, CommandContainerTest.class, ConfirmPaymentCommandTest.class,
	ConfirmPreparedPaymentCommandTest.class, CreatePaymentCommandTest.class,
	CreatePdfReportCommandTest.class, DeleteUserCommandTest.class,
	ListAccountsForSelectedCreditCardCommandTest.class,
	ListAllAccountsCommandTest.class, ListAllPaymentsCommandTest.class,
	ListUserAccountsCommandTest.class, ListUserPaymentsCommandTest.class,
	ListUsersCommandTest.class, LockAccountCommandTest.class,
	LockUserCommandTest.class, LockAccountCommandTest.class,
	LoginCommandTest.class, LogoutCommandTest.class,
	NoCommandTest.class, PreparePaymentCommandTest.class,
	RegistrateUserCommandTest.class, SendPaymentCommandTest.class,
	SortAccountsCommandTest.class, SortPaymentsCommandTest.class,
	UnlockAccountCommandTest.class, UnlockUserCommandTest.class,
	UpdateSettingsCommandTest.class, ViewSettingsCommandTest.class,
	CommandAccessFilterTest.class, EncodingFilterTest.class,
	NoCacheFilterTest.class, ContextListenerTest.class,
	ContextListenerTest.class, ControllerTest.class,
	PathTest.class})
public class AllTests {

}
