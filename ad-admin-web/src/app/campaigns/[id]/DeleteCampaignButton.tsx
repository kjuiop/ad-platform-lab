"use client";

import { useRouter } from "next/navigation";

export function DeleteCampaignButton({ id }: { id: string }) {
  const router = useRouter();

  async function handleDelete() {
    if (!confirm("이 캠페인을 삭제하시겠습니까?")) return;
    await fetch(`/api/campaigns/${id}`, { method: "DELETE" });
    router.push("/campaigns");
  }

  return (
    <button
      onClick={handleDelete}
      className="px-3 py-1 text-xs border border-red-300 text-red-600 rounded hover:bg-red-50"
    >
      삭제
    </button>
  );
}
