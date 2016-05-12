// пример # 3 : тег с атрибутом : HelloTag.java
package ua.nure.khainson.SummaryTask4.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ua.nure.khainson.SummaryTask4.db.LockStatus;

import java.io.IOException;

/**
 * User tag for jsp to obtain lock status Name by id.
 * 
 * @author P.Khainson
 * 
 */
public class GetLockStatusName extends TagSupport {
	private static final long serialVersionUID = -3501819072163761557L;
	private static final Logger LOG = Logger.getLogger(GetLockStatusName.class);
	private int lockStatusId;

	public void setLockStatusId(int lockStatusId) {
		this.lockStatusId = lockStatusId;
	}

	public int doStartTag() {
		// obtaining name of the lock status by id
		try {
			String name = LockStatus.getByOrdinal(lockStatusId).getName();
			pageContext.getOut().write(
					"<p class = \"" + name + "\">" + name + "</p>");
		} catch (IOException e) {
			LOG.error("errorMessage --> " + e.getMessage());
		}
		return SKIP_BODY;
	}
}
