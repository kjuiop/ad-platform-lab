import Link from "next/link";

export function Sidebar() {
  return (
    <aside className="w-60 border-r border-gray-200 bg-gray-50 p-6">
      <h1 className="text-lg font-bold mb-8">Ad Admin</h1>
      <nav className="flex flex-col gap-1">
        <Link
          href="/"
          className="px-3 py-2 rounded hover:bg-gray-200 text-sm text-gray-700"
        >
          대시보드
        </Link>
        <Link
          href="/campaigns"
          className="px-3 py-2 rounded hover:bg-gray-200 text-sm text-gray-700"
        >
          캠페인 관리
        </Link>
      </nav>
    </aside>
  );
}
