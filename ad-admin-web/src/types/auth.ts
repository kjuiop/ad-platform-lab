export type Role = "administrator" | "partnerAdmin" | "brandAdmin";

export interface User {
  id: number;
  email: string;
  name: string;
  role: Role;
  partnerId: number | null;
  brandId: number | null;
}
