"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";

export function DeleteCampaignButton({ id }: { id: string }) {
  const router = useRouter();
  const [loading, setLoading] = useState(false);

  async function handleDelete() {
    if (!confirm("이 캠페인을 삭제하시겠습니까?")) return;
    setLoading(true);
    try {
      const res = await fetch(`/api/campaigns/${id}`, { method: "DELETE" });
      if (!res.ok) {
        const body = await res.json().catch(() => null);
        alert(body?.error || `캠페인 삭제에 실패했습니다. (${res.status})`);
        return;
      }
      router.push("/campaigns");
      router.refresh();
    } finally {
      setLoading(false);
    }
  }

  return (
    <Button variant="outline" size="sm" disabled={loading} className="text-destructive border-destructive/30 hover:bg-destructive/10" onClick={handleDelete}>
      삭제
    </Button>
  );
}
