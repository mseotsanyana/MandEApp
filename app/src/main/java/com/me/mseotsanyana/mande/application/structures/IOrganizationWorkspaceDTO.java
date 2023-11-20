package com.me.mseotsanyana.mande.application.structures;

import java.util.List;

public interface IOrganizationWorkspaceDTO extends IRequestDTO{
    List<String> getWorkspaceMembers();
}
