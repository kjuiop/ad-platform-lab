import { User } from "@/types/auth";

export const mockUsers: { email: string; password: string; phone: string; user: User }[] = [
  {
    email: "admin@giglab.org",
    password: "admin1234",
    phone: "010-1111-1111",
    user: {
      id: 1,
      email: "admin@giglab.org",
      name: "슈퍼관리자",
      role: "administrator",
      partnerId: null,
      brandId: null,
    },
  },
  {
    email: "partner@abc.com",
    password: "partner1234",
    phone: "010-2222-2222",
    user: {
      id: 2,
      email: "partner@abc.com",
      name: "ABC유통 관리자",
      role: "partnerAdmin",
      partnerId: 1,
      brandId: null,
    },
  },
  {
    email: "brand@nike.com",
    password: "brand1234",
    phone: "010-3333-3333",
    user: {
      id: 3,
      email: "brand@nike.com",
      name: "나이키 담당자",
      role: "brandAdmin",
      partnerId: 1,
      brandId: 1,
    },
  },
];
