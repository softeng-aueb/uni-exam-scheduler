const canUseDOM = !!(
  typeof window !== "undefined" &&
  window.document &&
  window.document.createElement
);

type Props = {
  children?: any,
  fallback?: any
};

export default function BrowserOnly({ children, fallback }: Props) {
  if (!canUseDOM || children == null) {
    return fallback || null;
  }

  return <>{children()}</>;
}
