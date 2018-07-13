package baseinfo;

import org.apache.http.Header;

public class HttpResponse {

    private Header[] Headers;
    private String Body;
    private String ReasonPhrase;
    private int StatusCode;


    public int getStatusCode() {
        return StatusCode;
    }
    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }
    public Header[] getHeaders() {
        return Headers;
    }
    public void setHeaders(Header[] headers) {
        Headers = headers;
    }

    public String getBody() {

        return Body;
    }
    public void setBody(String body) {
        Body = body;
    }
    public String getReasonPhrase() {
        return ReasonPhrase;
    }
    public void setReasonPhrase(String reasonPhrase) {
        ReasonPhrase = reasonPhrase;
    }


}
