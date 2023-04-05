package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.annotations.SerializedName;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import uk.ac.stfc.facilities.mailing.api.exceptions.MailingListClientException;
import uk.ac.stfc.facilities.mailing.api.exceptions.NotFoundMailingListClientException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 *
 */
public class MailchimpInternalHttpClientTest {

    public static class TestDto {

        @SerializedName("value")
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public TestDto(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestDto testDto = (TestDto) o;

            return value != null ? value.equals(testDto.value) : testDto.value == null;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }

    // dash is required in the api key
    private static final MailchimpClientConfiguration EXAMPLE_CONFIGURATION =
            new MailchimpClientConfiguration("abcd-ab1","devproxy-api.facilities.rl.ac.uk/mailchimp/3.0");

    private static final IOException EXPECTED_IO_EXCEPTION_CAUSE = new IOException("test");
    private static final String TEST_DTO_JSON = "{\"value\":\"hello world\"}";
    private static final TestDto EXPECTED_TEST_DTO = new TestDto("hello world");

    public static Collection<Arguments> methodProvider() {

        Function<MailchimpInternalHttpClient, Callable> getFunction =
                httpClient -> () -> httpClient.get("", TestDto.class);

        Function<MailchimpInternalHttpClient, Callable> putFunction =
                httpClient -> () -> httpClient.put("", EXPECTED_TEST_DTO, TestDto.class);

        Function<MailchimpInternalHttpClient, Callable> postFunction =
                httpClient -> () -> httpClient.post("", EXPECTED_TEST_DTO, TestDto.class);

        Function<MailchimpInternalHttpClient, Callable> deleteFunction =
                httpClient -> () -> httpClient.delete("", TestDto.class);

        return Arrays.asList(
                Arguments.of(getFunction, HttpGet.class, false),
                Arguments.of(putFunction, HttpPut.class, true),
                Arguments.of(postFunction, HttpPost.class, true),
                Arguments.of(deleteFunction, HttpDelete.class, false)
        );
    }


    private CloseableHttpClient mockedHttpClient;
    private HttpContext mockedHttpContext;
    private CloseableHttpResponse mockedHttpResponse;
    private HttpEntity mockedHttpEntity;

    @BeforeEach
    public void setupHttpMock() {
        this.mockedHttpClient = mock(CloseableHttpClient.class);
        this.mockedHttpContext = mock(HttpContext.class);
        this.mockedHttpResponse = mock(CloseableHttpResponse.class, Mockito.RETURNS_DEEP_STUBS);
        this.mockedHttpEntity = mock(HttpEntity.class);
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    public void onRequest_successfulResponse_entitySet(
            Function<MailchimpInternalHttpClient, Callable<TestDto>> method,
            Class<? extends HttpUriRequest> methodType,
            boolean expectRequestBody
    ) throws Exception {

        MailchimpInternalHttpClient client = setupResponseWithBody(methodType);

        assertThat(method.apply(client).call()).isEqualTo(EXPECTED_TEST_DTO);

        if (expectRequestBody) {
            verifyExpectedRequestBody();
        }
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    public void onRequest_successfulResponse_correctResult(
            Function<MailchimpInternalHttpClient, Callable<TestDto>> method,
            Class<? extends HttpUriRequest> methodType
    ) throws Exception {

        MailchimpInternalHttpClient client = setupResponseWithBody(methodType);

        assertThat(method.apply(client).call()).isEqualTo(EXPECTED_TEST_DTO);
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    public void onRequest_unexpectedIOException_errorThrown(
            Function<MailchimpInternalHttpClient, Callable<TestDto>> method,
            Class<? extends HttpUriRequest> methodType
    ) throws Exception {

        MailchimpInternalHttpClient httpClient = setupIOExceptionOnRequestExecution(methodType);

        assertThatThrownBy(method.apply(httpClient)::call)
                .isInstanceOf(MailingListClientException.class)
                .hasCause(EXPECTED_IO_EXCEPTION_CAUSE);
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    public void onRequest_404_errorThrown(
            Function<MailchimpInternalHttpClient, Callable<TestDto>> method,
            Class<? extends HttpUriRequest> methodType
    ) throws Exception {

        MailchimpInternalHttpClient httpClient = setupResponseWithStatusCode(methodType, HttpStatus.SC_NOT_FOUND);

        assertThatThrownBy(method.apply(httpClient)::call)
                .isInstanceOf(NotFoundMailingListClientException.class)
                .hasNoCause();
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    public void onRequest_unexpectedStatusCode_errorThrown(
            Function<MailchimpInternalHttpClient, Callable<TestDto>> method,
            Class<? extends HttpUriRequest> methodType
    ) throws Exception {

        MailchimpInternalHttpClient httpClient = setupResponseWithStatusCode(methodType, HttpStatus.SC_INTERNAL_SERVER_ERROR);

        assertThatThrownBy(method.apply(httpClient)::call)
                .isInstanceOf(MailingListClientException.class)
                .hasNoCause();
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    public void onRequest_entityIOException_errorThrown(
            Function<MailchimpInternalHttpClient, Callable<TestDto>> method,
            Class<? extends HttpUriRequest> methodType
    ) throws Exception {

        MailchimpInternalHttpClient httpClient = setupExceptionWhileRetrievingContent(methodType);

        assertThatThrownBy(method.apply(httpClient)::call)
                .isInstanceOf(MailingListClientException.class)
                .hasCause(EXPECTED_IO_EXCEPTION_CAUSE);
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    public void onRequest_nullEntity_nothingReturned(
            Function<MailchimpInternalHttpClient, Callable<TestDto>> method,
            Class<? extends HttpUriRequest> methodType
    ) throws Exception {

        MailchimpInternalHttpClient httpClient = setupResponseWithNullEntity(methodType);

        assertThat(method.apply(httpClient).call())
                .isNull();
    }


    private void verifyExpectedRequestBody() throws IOException {
        ArgumentCaptor<HttpEntityEnclosingRequestBase> argumentCaptor
                = ArgumentCaptor.forClass(HttpEntityEnclosingRequestBase.class);
        verify(mockedHttpClient).execute(argumentCaptor.capture(), eq(mockedHttpContext));
        HttpEntityEnclosingRequestBase capturedArgument = argumentCaptor.getValue();

        String entityContent = EntityUtils.toString(capturedArgument.getEntity())
                .replaceAll("\\s", "");

        String expectedContent = TEST_DTO_JSON.replaceAll("\\s", "");

        assertThat(entityContent).isEqualTo(expectedContent);
    }

    private MailchimpInternalHttpClient setupExceptionWhileRetrievingContent(Class<? extends HttpUriRequest> requestClass) throws IOException {

        when(mockedHttpResponse.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);

        when(mockedHttpResponse.getEntity()).thenReturn(mockedHttpEntity);

        when(mockedHttpEntity.getContentLength()).thenReturn(100L);
        when(mockedHttpEntity.getContent()).thenThrow(EXPECTED_IO_EXCEPTION_CAUSE);

        when(mockedHttpClient.execute(isA(requestClass), eq(mockedHttpContext)))
                .thenReturn(mockedHttpResponse);


        return new MailchimpInternalHttpClient(EXAMPLE_CONFIGURATION, mockedHttpClient, mockedHttpContext);
    }

    private MailchimpInternalHttpClient setupResponseWithBody(Class<? extends HttpUriRequest> requestClass) throws IOException {

        when(mockedHttpResponse.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);

        when(mockedHttpResponse.getEntity()).thenReturn(new StringEntity(TEST_DTO_JSON));

        when(mockedHttpClient.execute(isA(requestClass), eq(mockedHttpContext)))
                .thenReturn(mockedHttpResponse);


        return new MailchimpInternalHttpClient(EXAMPLE_CONFIGURATION, mockedHttpClient, mockedHttpContext);
    }

    private MailchimpInternalHttpClient setupIOExceptionOnRequestExecution(Class<? extends HttpUriRequest> requestClass) throws IOException {

        when(mockedHttpClient.execute(isA(requestClass), eq(mockedHttpContext)))
                .thenThrow(EXPECTED_IO_EXCEPTION_CAUSE);

        return new MailchimpInternalHttpClient(EXAMPLE_CONFIGURATION, mockedHttpClient, mockedHttpContext);
    }

    private MailchimpInternalHttpClient setupResponseWithStatusCode(Class<? extends HttpUriRequest> requestClass, int statusCode) throws IOException {

        when(mockedHttpResponse.getStatusLine().getStatusCode()).thenReturn(statusCode);

        when(mockedHttpResponse.getEntity()).thenReturn(new StringEntity("hello world"));

        when(mockedHttpClient.execute(isA(requestClass), eq(mockedHttpContext)))
                .thenReturn(mockedHttpResponse);

        return new MailchimpInternalHttpClient(EXAMPLE_CONFIGURATION, mockedHttpClient, mockedHttpContext);
    }

    private MailchimpInternalHttpClient setupResponseWithNullEntity(Class<? extends HttpUriRequest> requestClass) throws IOException {

        when(mockedHttpResponse.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(mockedHttpResponse.getEntity()).thenReturn(null);

        when(mockedHttpClient.execute(isA(requestClass), eq(mockedHttpContext)))
                .thenReturn(mockedHttpResponse);

        return new MailchimpInternalHttpClient(EXAMPLE_CONFIGURATION, mockedHttpClient, mockedHttpContext);
    }
}
