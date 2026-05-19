import { AdStatus } from "@/types/ad";
import { Badge } from "@/components/ui/badge";

const statusConfig: Record<
  AdStatus,
  { label: string; variant: "default" | "secondary" | "destructive" | "outline" }
> = {
  DRAFT: { label: "작성중", variant: "secondary" },
  ACTIVE: { label: "운영중", variant: "default" },
  PAUSED: { label: "일시중지", variant: "outline" },
  COMPLETED: { label: "종료", variant: "destructive" },
};

export function StatusBadge({ status }: { status: AdStatus }) {
  const config = statusConfig[status];
  return <Badge variant={config.variant}>{config.label}</Badge>;
}
