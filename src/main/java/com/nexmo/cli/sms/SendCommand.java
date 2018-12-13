package com.nexmo.cli.sms;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexmo.cli.CommandFailedException;
import com.nexmo.cli.KeySecretCommand;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.messages.TextMessage;

import java.util.List;

@Parameters(commandDescription = "Send an SMS.")
public class SendCommand extends KeySecretCommand {

    @Parameter(names = {"--from",
            "--nexmo-number",
            "--name",
            "-f"}, description = "The number or name to send SMS from.", required = true)
    private String from;

    @Parameter(names = {"--to", "--number", "-t"}, description = "The number to send SMS to.", required = true)
    private String to;

    @Parameter(names = {"--message",
            "--body",
            "-m"}, description = "The message contents.", variableArity = true, required = true)
    private List<String> message;

    @Parameter(names = {"--unicode", "-u"}, description = "Whether or not unicode should be supported.")
    private boolean unicode;


    @Override
    protected void command() throws CommandFailedException {
        TextMessage message = new TextMessage(this.from, this.to, String.join(" ", this.message), this.unicode);
        try {
            SmsSubmissionResponse response = this.client.getSmsClient().submitMessage(message);
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (Exception e) {
            throw new CommandFailedException(getErrorMessage(), e);
        }
    }

    private String getErrorMessage() {
        return "Unable to send text message.";
    }
}
