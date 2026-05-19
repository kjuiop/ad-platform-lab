"use client";

import { AdStatus } from "@/types/ad";
import { useRouter } from "next/navigation";

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
  const actions = transitions[status];

  if (actions.length === 0) return null;

  async function handleChange(next: AdStatus) {
    await fetch(apiUrl, {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ status: next }),
    });
    router.refresh();
  }

  return (
    <div className="flex gap-2">
      {actions.map((action) => (
        <button
          key={action.next}
          onClick={() => handleChange(action.next)}
          className="px-3 py-1 text-xs border border-gray-300 rounded hover:bg-gray-100"
        >
          {action.label}
        </button>
      ))}
    </div>
  );
}
