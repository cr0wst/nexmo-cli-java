package com.nexmo.cli;

import com.beust.jcommander.Parameter;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientCreationException;

public abstract class KeySecretCommand implements Command {

    @Parameter(names = {"--key", "-k"}, description = "Your Nexmo API Key.", order = 0)
    private String key;

    @Parameter(names = {"--secret", "-s"}, description = "Your Nexmo API Secret.", order = 1)
    private String secret;

    protected NexmoClient client;

    protected abstract void command() throws CommandFailedException;

    @Override
    public void execute() throws CommandFailedException {
        try {
            this.client = new NexmoClient.Builder().apiKey(this.key).apiSecret(this.secret).build();
            command();
        } catch (NexmoClientCreationException ncce) {
            throw new CommandFailedException(ncce.getMessage(), ncce);
        }
    }
}
