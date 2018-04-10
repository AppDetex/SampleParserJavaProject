package com.appdetex.sampleparserjavaproject.lib.xargs;
import org.apache.commons.cli.*;

import java.util.Map;

public class Xargs {

    private CommandLine provider;
    private HelpFormatter formatter;
    private Options options;

    public void init(Map<String, Object> argParams, String[] args) throws ParseException, XargsInputException {

        this.options = new Options();

        Option inputUrl = new Option(
            argParams.get("opt").toString(),
            argParams.get("longOpt").toString(),
            (Boolean) argParams.get("hasArg"),
            argParams.get("description").toString());
        inputUrl.setRequired(true);
        this.options.addOption(inputUrl);

        CommandLineParser parser = new DefaultParser();
        this.formatter = new HelpFormatter();

        try {
            this.provider = parser.parse(this.options, args);

        } catch (ParseException e) {
            this.printHelp();

            throw new XargsInputException("Xargs: Input parse error");
        }
    }

    public String get(String name) {
        return this.provider.getOptionValue(name);
    }

    public void printHelp() {
        this.formatter.printHelp("scrape", this.options);
    }
}
