"use server";
import axios from 'axios';

export async function uploadFile(
  formData: FormData,
  payload: any
): Promise<any> {
  const { uploadType, type, academicYear, examPeriod } = payload;

  const url =
    type === "Academic Year"
      ? `${process.env.BACKEND_URL}/${uploadType}/upload?academicYearId=${academicYear}`
      : type === "Examination Period"
        ? `${process.env.BACKEND_URL}/${uploadType}/upload?examinationPeriodId=${examPeriod}`
        : '';
  console.log("=== URL == :", url)
  try {
    const { data } = await axios.post(url, formData);
    console.log("== Resp == :", data);
  } catch (e: any) {
    console.error("Error uploading file:", e);
    return { error: e.message };
  }
}
