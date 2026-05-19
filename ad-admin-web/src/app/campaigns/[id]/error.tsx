"use client";

import Link from "next/link";

export default function Error({ error }: { error: Error }) {
  return (
    <div className="text-center py-16">
      <p className="text-red-600 font-medium mb-2">
        캠페인을 불러올 수 없습니다
      </p>
      <p className="text-gray-500 text-sm mb-4">{error.message}</p>
      <Link
        href="/campaigns"
        className="text-blue-600 hover:underline text-sm"
      >
        ← 캠페인 목록으로
      </Link>
    </div>
  );
}
