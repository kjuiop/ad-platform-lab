"use client";

import { useState } from "react";
import { AdStatus } from "@/types/ad";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";

const transitions: Record<AdStatus, { label: string; next: AdStatus }[]> = {
  DRAFT: [{ label: "시작", next: "ACTIVE" }],
  ACTIVE: [
    { label: "일시중지", next: "PAUSED" },
    { label: "종료", next: "COMPLETED" },
  ],
  PAUSED: [
    { label: "재개", next: "ACTIVE" },
    { label: "종료", next: "COMPLETED" },
  ],
  COMPLETED: [],
};

interface StatusActionsProps {
  status: AdStatus;
  apiUrl: string;
}

export function StatusActions({ status, apiUrl }: StatusActionsProps) {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const actions = transitions[status];

  if (actions.length === 0) return null;

  async function handleChange(next: AdStatus) {
    setLoading(true);
    try {
      const res = await fetch(apiUrl, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status: next }),
      });
      if (!res.ok) {
        const body = await res.json().catch(() => null);
        alert(body?.error || `상태 변경에 실패했습니다. (${res.status})`);
        return;
      }
      router.refresh();
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="flex gap-2">
      {actions.map((action) => (
        <Button
          key={action.next}
          variant="outline"
          size="sm"
          disabled={loading}
          onClick={() => handleChange(action.next)}
        >
          {action.label}
        </Button>
      ))}
    </div>
  );
}
