import Link from "next/link";
import { Separator } from "@/components/ui/separator";

export function Sidebar() {
  return (
    <aside className="w-60 border-r bg-sidebar p-6">
      <h1 className="text-lg font-bold mb-2">Ad Admin</h1>
      <p className="text-xs text-muted-foreground mb-4">광고 관리 대시보드</p>
      <Separator className="mb-4" />
      <nav className="flex flex-col gap-1">
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
    </aside>
  );
}
