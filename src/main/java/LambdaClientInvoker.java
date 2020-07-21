import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;

public class LambdaClientInvoker {

    private final String FUNCTION_NAME;
    private final String PAYLOAD = "{\n" +
            " \"message1 \": \"Hello, World!\",\n" +
            " \"message2\": \"Epstein didn't kill himself\"\n" +
            "}";

    public LambdaClientInvoker(String functionName) {
        this.FUNCTION_NAME = functionName;
    }

    public static void main(String[] args) {
        String functionName = args[0];
        LambdaClientInvoker client = new LambdaClientInvoker(functionName);
        client.invokeLambda();
    }

    /**
     * Invokes the lambda and prints the returned result to console.
     */
    public void invokeLambda() {
        InvokeRequest invokeRequest = new InvokeRequest().withFunctionName(FUNCTION_NAME).withPayload(PAYLOAD);
        InvokeResult invokeResult = null;

        try {
            AWSLambda lambda = AWSLambdaClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).withRegion(Regions.US_EAST_2).build();
            invokeResult = lambda.invoke(invokeRequest);

            String answer = new String(invokeResult.getPayload().array());
            System.out.println("Lambda returned: " + answer);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        System.out.println(invokeResult.getStatusCode());
    }
}
