import { AdStatus } from "@/types/ad";

const statusConfig: Record<AdStatus, { label: string; className: string }> = {
  DRAFT: { label: "작성중", className: "bg-gray-100 text-gray-600" },
  ACTIVE: { label: "운영중", className: "bg-green-100 text-green-700" },
  PAUSED: { label: "일시중지", className: "bg-yellow-100 text-yellow-700" },
  COMPLETED: { label: "종료", className: "bg-red-100 text-red-600" },
};

export function StatusBadge({ status }: { status: AdStatus }) {
  const config = statusConfig[status];
  return (
    <span
      className={`px-2 py-0.5 rounded text-xs font-medium ${config.className}`}
    >
      {config.label}
    </span>
  );
}
