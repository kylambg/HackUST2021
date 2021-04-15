package handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import exception.InternalServerException;
import exception.ProposalNotExistException;
import proposal.AddProposalRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.jetbrains.annotations.Nullable;
import proposal.ProposalService;

import java.io.InputStream;
import java.util.Map;

public class ProposalDeleteRequestHandler extends ServerRequestHandler{

    private static final String PATH_PREFIX = "/proposal/";
    public ProposalDeleteRequestHandler() {
    }
    @Override
    public void handle(String httpMethod, String path, Map<String, String> param, @Nullable InputStream requestBody, HttpResponse response) throws InternalServerException {
        if (!httpMethod.equalsIgnoreCase("DELETE")) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            return;
        }

        if (!path.startsWith(PATH_PREFIX)) {
            response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        String idFromURL = path.substring(PATH_PREFIX.length());

        try {
            boolean success = ProposalService.getInstance().deleteProposal(Integer.parseInt(idFromURL));
            if (!success) {
                response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                return;
            }
            response.setStatusCode(HttpStatus.SC_OK);
        } catch (ProposalNotExistException e) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            response.setReasonPhrase("No Proposal record");
        } catch (NumberFormatException e){
            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        }
    }

}
