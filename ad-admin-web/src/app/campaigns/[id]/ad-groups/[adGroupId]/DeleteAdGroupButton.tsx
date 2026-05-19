"use client";

import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";

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
    <Button
      variant="outline"
      size="sm"
      className="text-destructive border-destructive/30 hover:bg-destructive/10"
      onClick={handleDelete}
    >
      삭제
    </Button>
  );
}
