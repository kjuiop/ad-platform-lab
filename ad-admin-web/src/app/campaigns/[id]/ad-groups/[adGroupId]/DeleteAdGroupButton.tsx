"use client";

import { useRouter } from "next/navigation";

export function DeleteAdGroupButton({
  campaignId,
  adGroupId,
}: {
  campaignId: string;
  adGroupId: string;
}) {
  const router = useRouter();

  async function handleDelete() {
    if (!confirm("이 광고그룹을 삭제하시겠습니까?")) return;
    await fetch(`/api/ad-groups/${adGroupId}`, { method: "DELETE" });
    router.push(`/campaigns/${campaignId}`);
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
