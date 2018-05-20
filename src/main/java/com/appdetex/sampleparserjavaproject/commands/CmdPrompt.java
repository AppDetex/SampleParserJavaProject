package com.appdetex.sampleparserjavaproject.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CmdPrompt {
    private String websiteUrl;
}
