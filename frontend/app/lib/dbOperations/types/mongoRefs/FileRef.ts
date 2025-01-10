const getSizeInMB = (size: string): string => {
  try {
    if (isNaN(Number(size))) {
      return size;
    }
    const sizeInKB = Math.floor(Number(size) / 1024);
    if (sizeInKB < 1024) {
      return `${sizeInKB} KB`;
    }
    return `${Math.floor(sizeInKB / 1024)} MB`;
  } catch (e: any) {
    return "NaN";
  }
};

export type MongoFileRefType = {
  file: string,
  path: string,
  name: string,
  type: string,
  size: string
}

export const getFileRef = ({ filepath, listSize }: any): MongoFileRefType => {
  const properSplitter = "/";
  const splitter = process.platform === "win32" ? "\\" : properSplitter;
  const _filepath = filepath.split(splitter).filter((i: any) => i !== "").join(properSplitter);
  const idx = _filepath.lastIndexOf(properSplitter);
  const path = properSplitter + _filepath.slice(0, idx + 1);
  const name = _filepath.slice(idx + 1);
  const type = _filepath.slice(_filepath.lastIndexOf(".") + 1);

  return { file: `${path}${name}`, path, name, type, size: getSizeInMB(listSize) };
};
