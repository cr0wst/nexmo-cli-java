package com.nexmo.cli.insight;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexmo.cli.CommandFailedException;
import com.nexmo.cli.KeySecretCommand;
import com.nexmo.client.insight.BasicInsightResponse;

@Parameters(commandDescription = "Look up a number using the basic number insights.")
public class BasicCommand extends KeySecretCommand {
    @Parameter(names = {"--number",
            "-n"}, description = "The number to look up without any formatting.", required = true)
    private String number;

    @Parameter(names = {"--country", "-c"}, description = "The country code for the number")
    private String country;

    @Override
    protected void command() throws CommandFailedException {
        try {
            BasicInsightResponse response = this.client
                    .getInsightClient()
                    .getBasicNumberInsight(this.number, this.country);
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (Exception e) {
            throw new CommandFailedException(getErrorMessage(), e);
        }
    }

    private String getErrorMessage() {
        return "Unable to perform basic number insights.";
    }
}
