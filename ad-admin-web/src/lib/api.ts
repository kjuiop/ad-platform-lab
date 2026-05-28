export function getAppUrl(): string {
  return process.env.APP_URL || "http://localhost:3001";
}

export function getBackendUrl(): string {
  return process.env.BACKEND_URL || "http://localhost:8081";
}
