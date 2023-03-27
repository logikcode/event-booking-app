package com.bw.reference.domain.account;

import com.bw.reference.entity.Organisation;
import com.bw.reference.entity.WorkspaceUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {

    private String userId;

    private String authToken;

    private WorkspaceUser workspaceUser;

    private Organisation organisation;
}
