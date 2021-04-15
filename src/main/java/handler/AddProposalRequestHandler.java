package handler;

import org.jetbrains.annotations.NotNull;
import proposal.AddProposalRequest;
import proposal.ProposalService;
import exception.InternalServerException;
import exception.ProposalExistException;
import org.apache.http.*;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AddProposalRequestHandler extends JsonRequestHandler<AddProposalRequest> {

    public AddProposalRequestHandler() {
        super(AddProposalRequest.class);
    }


    public void handleJson(String httpMethod, String path, Map<String, String> param, @NotNull AddProposalRequest requestBody, HttpResponse response) throws InternalServerException {
        if (!httpMethod.equalsIgnoreCase("POST")) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            return;
        }

        int id = ProposalService.getInstance().addProposal(requestBody);
        response.setStatusCode(HttpStatus.SC_CREATED);
        response.setHeader("Location", "/books/" + id);
    }
}