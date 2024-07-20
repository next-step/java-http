package jakarta;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        var httpServletResponse = new HttpServletResponse();

        if (httpRequest.isGet()) {
            doGet(HttpServletRequest.from(httpRequest), httpServletResponse);
            httpResponse.update(httpServletResponse);
            return;
        }
        if (httpRequest.isPost()) {
            doPost(HttpServletRequest.from(httpRequest), httpServletResponse);
            httpResponse.update(httpServletResponse);
            return;
        }
        throw new UnsupportedOperationException("Unsupported HTTP method: " + httpRequest.getRequestLine().getMethod());
    }

    protected abstract void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception;

    protected abstract void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception;
}
