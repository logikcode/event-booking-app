package com.bw.reference.modelfactory;

import com.github.heywhy.springentityfactory.contracts.ModelFactory;

public class ModelFactoryRegistrar {
    public static void register(ModelFactory modelFactory) {
        modelFactory.register(PortalUserFactory.class);
        modelFactory.register(PortalAccountFactory.class);
        modelFactory.register(PortalAccountTypeRoleFactory.class);
        modelFactory.register(BwFileFactory.class);
        modelFactory.register(PortalAccountMemberRoleFactory.class);
        modelFactory.register(PortalAccountMembershipFactory.class);
        modelFactory.register(CountryFactory.class);
        modelFactory.register(StateFactory.class);
        modelFactory.register(UserRegistrationDtoFactory.class);
        modelFactory.register(CityFactory.class);
        modelFactory.register(WorkspaceUserImageFactory.class);
        modelFactory.register(AddressFactory.class);
        modelFactory.register(RolePermissionFactory.class);
        modelFactory.register(PortalUserAddressFactory.class);
        modelFactory.register(ActivityLogFactory.class);
        modelFactory.register(ContactFactory.class);
        modelFactory.register(OccupationFactory.class);
    }
}
