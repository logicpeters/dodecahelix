package com.ddxlabs.consola.response;

public interface ResponseBuilder {

    /**
     *  Finalize the response.
     * @return
     */
    public Response build();

    /**
     *  Add a newline.
     *
     *  If the previous line is a fragment, this will start a new line.
     *  Otherwise this will add an empty line.
     *
     * @return
     */
    public ResponseBuilder newline();

    /**
     *  Add a full line (default style) followed by a newline character.
     *
     * @param message
     * @return
     */
    public ResponseBuilder line(String message);

    /**
     *  Add a full line (configurable style) followed by a newline character.
     *
     * @param message
     * @param style
     * @return
     */
    public ResponseBuilder line(String message, TextStyle style);

    /**
     *   Add a fragment of styled text without a newline at the end (can continue to add fragments).
     *
     * @param fragment
     * @param style
     * @return
     */
    public ResponseBuilder fragment(String fragment, TextStyle style);

    public ResponseBuilder subject(String subject);

    public ResponseBuilder resource(ResponseResource resource);

}
