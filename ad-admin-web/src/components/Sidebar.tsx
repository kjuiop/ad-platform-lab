import Link from "next/link";
import { Separator } from "@/components/ui/separator";
import { LogoutButton } from "@/components/LogoutButton";
import { cookies } from "next/headers";
import { mockUsers } from "@/lib/mock-users";

async function getUser() {
  const cookieStore = await cookies();
  const sessionId = cookieStore.get("session")?.value;
  if (!sessionId) return null;
  const found = mockUsers.find((u) => u.user.id === Number(sessionId));
  return found?.user ?? null;
}

const roleLabels = {
  administrator: "슈퍼관리자",
  partnerAdmin: "파트너관리자",
  brandAdmin: "브랜드관리자",
};

export async function Sidebar() {
  const user = await getUser();

  return (
    <aside className="w-60 border-r bg-sidebar p-6 flex flex-col">
      <h1 className="text-lg font-bold mb-2">Ad Admin</h1>
      <p className="text-xs text-muted-foreground mb-4">광고 관리 대시보드</p>
      <Separator className="mb-4" />
      <nav className="flex flex-col gap-1 flex-1">
        <Link
          href="/"
          className="px-3 py-2 rounded-md hover:bg-sidebar-accent text-sm text-sidebar-foreground"
        >
          대시보드
        </Link>
        <Link
          href="/campaigns"
          className="px-3 py-2 rounded-md hover:bg-sidebar-accent text-sm text-sidebar-foreground"
        >
          캠페인 관리
        </Link>
      </nav>

      {user && (
        <div className="mt-auto pt-4 border-t">
          <p className="text-sm font-medium truncate">{user.name}</p>
          <p className="text-xs text-muted-foreground mb-3">
            {roleLabels[user.role]}
          </p>
          <LogoutButton />
        </div>
      )}
    </aside>
  );
}
