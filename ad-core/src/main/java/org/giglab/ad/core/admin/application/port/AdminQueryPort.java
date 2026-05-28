package org.giglab.ad.core.admin.application.port;

public interface AdminQueryPort {

  boolean existsByEmail(String email);
}
