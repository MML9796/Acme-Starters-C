
package acme.features.any.auditor;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.realms.Auditor;

@Controller
public class AnyAuditorController extends AbstractController<Any, Auditor> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("show", AnyAuditorShowService.class);
	}

}
