"use client";

export default function Error({
  error,
  reset,
}: {
  error: Error;
  reset: () => void;
}) {
  return (
    <div className="text-center py-16">
      <p className="text-red-600 font-medium mb-2">오류가 발생했습니다</p>
      <p className="text-gray-500 text-sm mb-4">{error.message}</p>
      <button
        onClick={reset}
        className="px-4 py-2 border border-gray-300 text-sm rounded hover:bg-gray-50"
      >
        다시 시도
      </button>
    </div>
  );
}
