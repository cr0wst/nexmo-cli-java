package com.nexmo.cli.insight;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexmo.cli.CommandFailedException;
import com.nexmo.cli.KeySecretCommand;
import com.nexmo.client.insight.AdvancedInsightRequest;
import com.nexmo.client.insight.AdvancedInsightResponse;

@Parameters(commandDescription = "Look up a number using the advanced number insights.")
public class AdvancedCommand extends KeySecretCommand {
    @Parameter(names = {"--number",
            "-n"}, description = "The number to look up without any formatting.", required = true)
    private String number;

    @Parameter(names = {"--country", "-c"}, description = "The country code for the number")
    private String country;

    @Parameter(names = "--cnam", description = "Whether or not the owner's name should be looked up.")
    private boolean cnam;

    @Parameter(names = {"--ip",
            "-i"}, description = "The IP address of the user. We will compare this to the country they are in to see if it matches.")
    private String ipAddress;

    @Override
    protected void command() throws CommandFailedException {
        try {
            AdvancedInsightResponse response = this.client
                    .getInsightClient()
                    .getAdvancedNumberInsight(new AdvancedInsightRequest.Builder(this.number)
                            .country(this.country)
                            .cnam(this.cnam)
                            .ipAddress(this.ipAddress)
                            .build());
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (Exception e) {
            throw new CommandFailedException(getErrorMessage(), e);
        }
    }

    private String getErrorMessage() {
        return "Unable to advanced basic number insights.";
    }
}
