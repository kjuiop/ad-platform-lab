"use client";

import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";

export function DeleteCampaignButton({ id }: { id: string }) {
  const router = useRouter();

  async function handleDelete() {
    if (!confirm("이 캠페인을 삭제하시겠습니까?")) return;
    await fetch(`/api/campaigns/${id}`, { method: "DELETE" });
    router.push("/campaigns");
  }

  return (
    <Button variant="outline" size="sm" className="text-destructive border-destructive/30 hover:bg-destructive/10" onClick={handleDelete}>
      삭제
    </Button>
  );
}
