package py.console.errorcode;

import org.junit.Test;
import py.test.TestBase;

import java.io.FileWriter;
import java.io.IOException;

public class CreateApiErrors extends TestBase {

  @Test
  public void createApiErrors() throws IOException {

//        OK(ApiErrorCode.OK, ApiErrorMsg.OK),

    FileWriter errorsFile = new FileWriter("/tmp/Errors.java");
    FileWriter errCodeMsg = new FileWriter("/tmp/ApiErrorMsg.java");

    for (Errors error : Errors.values()) {
      errorsFile.write(String.format("%s(%s, \"%s\"),\r\n", error.name(), error.getErrorCode(),
          error.getDescription()));

//            errorsFile.write(String.format("%s(ApiErrorCode.ErrNo_%s, ApiErrorMsg.ErrMsg_%s),\r\n", error.name(), error.name(), error.name()));
//            errCodeMsg.write(String.format("public static final String ErrMsg_%s = \"\";\r\n", error.name()));
    }
    errCodeMsg.flush();
    errCodeMsg.close();
    errorsFile.flush();
    errorsFile.close();


  }

}
