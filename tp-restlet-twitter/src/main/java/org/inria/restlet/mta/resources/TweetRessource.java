package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class TweetRessource extends ServerResource {

	/** Backend. */
	private Backend backend_;

	/** Utilisateur géré par cette resource. */
	private User user_;

	/**
     * Constructor.
     * Call for every single user request.
     */
    public TweetRessource()
    {
        backend_ = (Backend) getApplication().getContext().getAttributes()
                .get("backend");
    }

	@Post("json")
	public Representation postTweet(JsonRepresentation representation) throws Exception {
		String userIdString = (String) getRequest().getAttributes().get("userId");
		String tweet = (String) getRequest().getAttributes().get("tweets");
		int userId = Integer.valueOf(userIdString);
		if (backend_.getDatabase().exists(userId)) {
			backend_.getDatabase().addTweet(tweet);
			JSONObject response = new JSONObject();
			response.put("result", "DONE");
			return new JsonRepresentation(response);
		} else {
			JSONObject response = new JSONObject();
			response.put("result", "DOES NOT EXIST");
			return new JsonRepresentation(response);
		}
	}

}
