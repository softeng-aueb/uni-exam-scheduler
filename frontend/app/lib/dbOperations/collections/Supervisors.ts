// >>> TEMPLATE FOR CRUD
"use server";

import axios from 'axios';

const url = `${process.env.BACKEND_URL}/supervisors`

export async function createSupervisor(data: any): Promise<any> {
  try {
    const { data: createdResp } = await axios.post(url, data);
    return createdResp;

  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readSupervisor(id: any): Promise<any> {
  try {
    const { data: course } = await axios.get(`${url}/${id}`);
    return course;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readSupervisors(): Promise<any> {
  try {
    const { data: supervisors } = await axios.get(url);
    return supervisors;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function deleteSupervisor(examId: string, supervisorId:string): Promise<any> {
  const deleteSupervisorUrl = `${process.env.BACKEND_URL}/examinations/${examId}/supervisions/${supervisorId}`
  try {
    const { data: deletedResp } = await axios.delete(deleteSupervisorUrl);
    return deletedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function updateSupervisor(id: string, updateObj: any): Promise<any> {
  try {
    const { data: updatedResp } = await axios.put(`${url}/${id}`, updateObj);
    return updatedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function addSupervisorToExam(examinationId: string, payload): Promise<any> {
  const supervisorUrl =  `${process.env.BACKEND_URL}/examinations/${examinationId}/supervisions`
  try {
    const { data: addedResp } = await axios.post(supervisorUrl, payload);
    return addedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function deleteSupervisorToExam(examinationId: string, supervisionId): Promise<any> {
  const supervisorUrl =  `${process.env.BACKEND_URL}/examinations/${examinationId}/supervisions/${supervisionId}`
  try {
    const { data: deletedResp } = await axios.delete(supervisorUrl);
    return deletedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function deleteSupervision(examinationId: string): Promise<any> {
  const supervisorUrl =  `${process.env.BACKEND_URL}/supervisions?examinationPeriodId=${examinationId}`
  try {
    const { data: deletedResp } = await axios.delete(supervisorUrl);
    return deletedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}


