package com.appdetex.sampleparserjavaproject.lib.xargs;

import com.appdetex.sampleparserjavaproject.lib.Cons;
import org.apache.commons.cli.*;

import java.util.Map;

public class Xargs {

    private Map<String, Object> argParams;
    private Cons console;
    private CommandLine provider;

    public Xargs(Cons console) {
        this.console = console;
    }

    public void init(Map<String, Object> argParams, String[] args) throws ParseException, XargsInputException {

        Options options = new Options();

        Option inputUrl = new Option(
            argParams.get("opt").toString(),
            argParams.get("longOpt").toString(),
            true,
            argParams.get("description").toString());

        inputUrl.setRequired(true);
        options.addOption(inputUrl);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        /**
         * Refector Extract validation class
         */
        try {
            this.provider = parser.parse(options, args);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("scrape", options);

            throw new XargsInputException("Xargs: Input parse error");
        }
    }

    public String get(String name) {
        return this.provider.getOptionValue(name);
    }
}
