package com.company.project.controller;

import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Health endpoints for Kubernetes probes.
 *
 * Liveness and readiness answer different questions on purpose:
 *  - /healthz (liveness):  is the process alive? No dependency checks — a failing
 *      liveness probe RESTARTS the container, so it must not depend on the DB
 *      (otherwise a brief DB outage would restart every app Pod = cascading failure).
 *  - /readyz (readiness):  can we serve traffic right now? Checks the DB, because the
 *      app is useless without it. A failing readiness probe just removes the Pod from
 *      the Service's endpoints (no restart) until it recovers.
 */
@RestController
public class HealthController {

  private final DataSource dataSource;

  public HealthController(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @GetMapping("/healthz")
  public ResponseEntity<String> healthz() {
    return ResponseEntity.ok("healthy");
  }

  @GetMapping("/readyz")
  public ResponseEntity<String> readyz() {
    try (var connection = dataSource.getConnection()) {
      if (connection.isValid(2)) {
        return ResponseEntity.ok("ready");
      }
    } catch (Exception ignored) {
      // fall through to 503
    }
    return ResponseEntity.status(503).body("not-ready");
  }
}
