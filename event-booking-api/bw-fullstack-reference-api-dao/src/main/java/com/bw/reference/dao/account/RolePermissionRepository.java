package com.bw.reference.dao.account;

import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.entity.RolePermission;
import com.bw.reference.enums.PermissionTypeConstant;
import com.bw.enums.GenericStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Query("select r from RolePermission r where r.portalAccountTypeRole = ?1 and r.name = ?2 and r.status = ?3")
    Optional<RolePermission> findByRoleAndAccountTypeAndStatus(PortalAccountTypeRole role, PermissionTypeConstant permission, GenericStatusConstant status);

    @Query("SELECT r FROM RolePermission r JOIN FETCH r.portalAccountTypeRole WHERE r.portalAccountTypeRole IN :roles")
    List<RolePermission> findAllByRoleIn(@Param("roles") List<PortalAccountTypeRole> roles);

}
