
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AuthenticatedAuditorCreateService extends AbstractService<Authenticated, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditorRepository	repository;

	private Auditor							auditor;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.auditor = super.newObject(Auditor.class);
		this.auditor.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditor, "firm", "highlights", "solicitor");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditor);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditor);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditor, "firm", "highlights", "solicitor");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
