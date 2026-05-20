import { Creative } from "@/types/ad";

function isSafeUrl(url: string): boolean {
  try {
    const parsed = new URL(url);
    return parsed.protocol === "http:" || parsed.protocol === "https:";
  } catch {
    return false;
  }
}

export function CreativePreview({ creative }: { creative: Creative }) {
  const safe = isSafeUrl(creative.clickUrl);
  return (
    <div className="border border-gray-200 bg-gradient-to-r from-gray-50 to-white p-4 rounded-lg max-w-sm relative">
      <span className="absolute top-2 right-2 px-1.5 py-0.5 text-[10px] font-medium text-blue-400 bg-blue-50 rounded">
        광고
      </span>
      <a
        href={safe ? creative.clickUrl : "#"}
        className={`block pr-8 ${!safe ? "pointer-events-none opacity-60" : ""}`}
        rel="noopener noreferrer"
      >
        <p className="font-semibold text-sm text-gray-800">{creative.title}</p>
        {creative.description && (
          <p className="text-gray-500 text-xs mt-1 leading-relaxed">
            {creative.description}
          </p>
        )}
      </a>
    </div>
  );
}
