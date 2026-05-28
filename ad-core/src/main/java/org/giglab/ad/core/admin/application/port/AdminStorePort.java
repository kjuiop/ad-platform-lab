package org.giglab.ad.core.admin.application.port;

import org.giglab.ad.core.admin.domain.entity.Admin;

public interface AdminStorePort {

  boolean existsByEmail(String email);

  Admin store(Admin admin);
}
