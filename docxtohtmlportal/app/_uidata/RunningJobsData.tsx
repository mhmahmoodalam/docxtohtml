/*
# Copyright 2024 Muhammed Mahmood Alam
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 */

const RunningJobStates =
  [
    {
      jobId: 1,
      state: "Processing",
      name: "Enim.mp3",
      date: "8/16/2023",
      progress: 40,
      preview: false,
      previewId: 78622,
    },
    {
      jobId: 2,
      state: "Completed",
      name: "AmetNullaQuisque.mp3",
      date: "8/25/2023",
      progress: 98,
      preview: true,
      previewId: 54949,
    },
    {
      jobId: 3,
      state: "Completed",
      name: "VolutpatQuam.tiff",
      date: "3/26/2024",
      progress: 74,
      preview: false,
      previewId: 65855,
    },
    {
      jobId: 4,
      state: "Processing",
      name: "VestibulumSagittisSapien.tiff",
      date: "7/16/2023",
      progress: 87,
      preview: true,
      previewId: 84652,
    },
    {
      jobId: 5,
      state: "Failed",
      name: "PretiumIaculis.mp3",
      date: "7/12/2023",
      progress: 18,
      preview: true,
      previewId: 2641,
    },
    {
      jobId: 6,
      state: "Completed",
      name: "SemMaurisLaoreet.pdf",
      date: "5/2/2023",
      progress: 99,
      preview: true,
      previewId: 94815,
    },
    {
      jobId: 7,
      state: "Processing",
      name: "Et.txt",
      date: "2/28/2024",
      progress: 73,
      preview: false,
      previewId: 91995,
    },
    {
      jobId: 8,
      state: "Failed",
      name: "NibhInLectus.mp3",
      date: "3/22/2024",
      progress: 92,
      preview: false,
      previewId: 99268,
    },
    {
      jobId: 9,
      state: "Failed",
      name: "Libero.mov",
      date: "1/1/2024",
      progress: 97,
      preview: true,
      previewId: 54759,
    },
    {
      jobId: 10,
      state: "Completed",
      name: "Consequat.jpeg",
      date: "12/29/2023",
      progress: 11,
      preview: false,
      previewId: 97976,
    },
    {
      jobId: 11,
      state: "Completed",
      name: "ConsequatNulla.mp3",
      date: "2/29/2024",
      progress: 79,
      preview: false,
      previewId: 81015,
    },
    {
      jobId: 12,
      state: "Processing",
      name: "Venenatis.tiff",
      date: "2/27/2024",
      progress: 56,
      preview: true,
      previewId: 49950,
    },
    {
      jobId: 13,
      state: "Completed",
      name: "Diam.mp3",
      date: "12/17/2023",
      progress: 13,
      preview: true,
      previewId: 29680,
    },
    {
      jobId: 14,
      state: "Processing",
      name: "VivamusVestibulumSagittis.xls",
      date: "11/11/2023",
      progress: 6,
      preview: false,
      previewId: 36973,
    },
    {
      jobId: 15,
      state: "Completed",
      name: "LuctusNecMolestie.ppt",
      date: "9/10/2023",
      progress: 87,
      preview: false,
      previewId: 98196,
    },
    {
      jobId: 16,
      state: "Completed",
      name: "Leo.ppt",
      date: "8/25/2023",
      progress: 64,
      preview: true,
      previewId: 90848,
    },
    {
      jobId: 17,
      state: "Completed",
      name: "DonecQuisOrci.tiff",
      date: "12/31/2023",
      progress: 3,
      preview: false,
      previewId: 59047,
    },
    {
      jobId: 18,
      state: "Failed",
      name: "CursusVestibulum.ppt",
      date: "4/25/2023",
      progress: 87,
      preview: true,
      previewId: 84405,
    },
    {
      jobId: 19,
      state: "Processing",
      name: "PedeLobortisLigula.mp3",
      date: "7/13/2023",
      progress: 14,
      preview: true,
      previewId: 86965,
    },
    {
      jobId: 20,
      state: "Processing",
      name: "CubiliaCurae.mp3",
      date: "11/1/2023",
      progress: 60,
      preview: false,
      previewId: 24335,
    },
    {
      jobId: 21,
      state: "Completed",
      name: "In.avi",
      date: "11/1/2023",
      progress: 1,
      preview: false,
      previewId: 38471,
    },
    {
      jobId: 22,
      state: "Failed",
      name: "CrasMi.mp3",
      date: "9/4/2023",
      progress: 75,
      preview: false,
      previewId: 26484,
    },
    {
      jobId: 23,
      state: "Completed",
      name: "PrimisInFaucibus.avi",
      date: "10/1/2023",
      progress: 44,
      preview: true,
      previewId: 35578,
    },
    {
      jobId: 24,
      state: "Processing",
      name: "UrnaPretium.mpeg",
      date: "10/9/2023",
      progress: 45,
      preview: true,
      previewId: 38244,
    },
    {
      jobId: 25,
      state: "Completed",
      name: "Luctus.ppt",
      date: "2/25/2024",
      progress: 63,
      preview: true,
      previewId: 66062,
    },
    {
      jobId: 26,
      state: "Failed",
      name: "FeugiatNon.avi",
      date: "5/18/2023",
      progress: 76,
      preview: false,
      previewId: 21281,
    },
    {
      jobId: 27,
      state: "Failed",
      name: "TortorSollicitudinMi.avi",
      date: "6/14/2023",
      progress: 76,
      preview: true,
      previewId: 60148,
    },
    {
      jobId: 28,
      state: "Failed",
      name: "NatoquePenatibus.txt",
      date: "9/24/2023",
      progress: 31,
      preview: false,
      previewId: 71590,
    },
    {
      jobId: 29,
      state: "Completed",
      name: "Justo.avi",
      date: "7/13/2023",
      progress: 70,
      preview: true,
      previewId: 58224,
    },
    {
      jobId: 30,
      state: "Processing",
      name: "Etiam.jpeg",
      date: "3/6/2024",
      progress: 7,
      preview: true,
      previewId: 97099,
    },
    {
      jobId: 31,
      state: "Failed",
      name: "VelAugue.avi",
      date: "5/1/2023",
      progress: 10,
      preview: false,
      previewId: 15397,
    },
    {
      jobId: 32,
      state: "Completed",
      name: "Donec.tiff",
      date: "10/27/2023",
      progress: 39,
      preview: false,
      previewId: 46393,
    },
    {
      jobId: 33,
      state: "Processing",
      name: "EratVolutpatIn.jpeg",
      date: "2/16/2024",
      progress: 70,
      preview: false,
      previewId: 98326,
    },
    {
      jobId: 34,
      state: "Processing",
      name: "AtNulla.ppt",
      date: "1/5/2024",
      progress: 92,
      preview: false,
      previewId: 79543,
    },
    {
      jobId: 35,
      state: "Processing",
      name: "Justo.xls",
      date: "2/18/2024",
      progress: 69,
      preview: false,
      previewId: 16952,
    },
    {
      jobId: 36,
      state: "Failed",
      name: "PlateaDictumstMaecenas.xls",
      date: "6/28/2023",
      progress: 40,
      preview: true,
      previewId: 45106,
    },
    {
      jobId: 37,
      state: "Completed",
      name: "Ornare.ppt",
      date: "1/7/2024",
      progress: 76,
      preview: false,
      previewId: 33419,
    },
    {
      jobId: 38,
      state: "Failed",
      name: "MaecenasTincidunt.mp3",
      date: "1/14/2024",
      progress: 65,
      preview: false,
      previewId: 2730,
    },
    {
      jobId: 39,
      state: "Completed",
      name: "Felis.mp3",
      date: "5/4/2023",
      progress: 1,
      preview: false,
      previewId: 15009,
    },
    {
      jobId: 40,
      state: "Processing",
      name: "Risus.ppt",
      date: "7/21/2023",
      progress: 4,
      preview: true,
      previewId: 8699,
    },
    {
      jobId: 41,
      state: "Processing",
      name: "EnimLorem.jpeg",
      date: "7/12/2023",
      progress: 2,
      preview: false,
      previewId: 39244,
    },
    {
      jobId: 42,
      state: "Failed",
      name: "SuspendisseOrnare.tiff",
      date: "2/5/2024",
      progress: 79,
      preview: true,
      previewId: 83750,
    },
    {
      jobId: 43,
      state: "Failed",
      name: "DapibusDuisAt.pdf",
      date: "2/14/2024",
      progress: 93,
      preview: false,
      previewId: 17100,
    },
    {
      jobId: 44,
      state: "Completed",
      name: "Neque.avi",
      date: "2/6/2024",
      progress: 24,
      preview: false,
      previewId: 19782,
    },
    {
      jobId: 45,
      state: "Completed",
      name: "NisiNam.mpeg",
      date: "11/10/2023",
      progress: 19,
      preview: false,
      previewId: 12694,
    },
    {
      jobId: 46,
      state: "Processing",
      name: "Quisque.mp3",
      date: "1/1/2024",
      progress: 44,
      preview: false,
      previewId: 64467,
    },
    {
      jobId: 47,
      state: "Completed",
      name: "VestibulumVestibulumAnte.gif",
      date: "9/8/2023",
      progress: 23,
      preview: false,
      previewId: 41246,
    },
    {
      jobId: 48,
      state: "Completed",
      name: "Donec.tiff",
      date: "8/10/2023",
      progress: 66,
      preview: true,
      previewId: 41626,
    },
    {
      jobId: 49,
      state: "Processing",
      name: "EleifendPede.avi",
      date: "6/16/2023",
      progress: 2,
      preview: false,
      previewId: 48431,
    },
    {
      jobId: 50,
      state: "Completed",
      name: "HacHabitasse.xls",
      date: "5/17/2023",
      progress: 13,
      preview: false,
      previewId: 18059,
    },
    {
      jobId: 51,
      state: "Processing",
      name: "MagnisDis.mpeg",
      date: "12/11/2023",
      progress: 99,
      preview: false,
      previewId: 13721,
    },
    {
      jobId: 52,
      state: "Failed",
      name: "DuiMaecenas.tiff",
      date: "9/6/2023",
      progress: 22,
      preview: false,
      previewId: 3868,
    },
    {
      jobId: 53,
      state: "Failed",
      name: "Donec.txt",
      date: "5/9/2023",
      progress: 51,
      preview: false,
      previewId: 25535,
    },
    {
      jobId: 54,
      state: "Processing",
      name: "AmetEleifend.jpeg",
      date: "2/15/2024",
      progress: 77,
      preview: true,
      previewId: 78545,
    },
    {
      jobId: 55,
      state: "Processing",
      name: "Egestas.doc",
      date: "6/16/2023",
      progress: 69,
      preview: true,
      previewId: 98109,
    },
    {
      jobId: 56,
      state: "Processing",
      name: "Rutrum.mp3",
      date: "7/27/2023",
      progress: 46,
      preview: true,
      previewId: 39724,
    },
    {
      jobId: 57,
      state: "Failed",
      name: "SemSed.doc",
      date: "4/14/2023",
      progress: 34,
      preview: true,
      previewId: 91995,
    },
    {
      jobId: 58,
      state: "Completed",
      name: "Magnis.doc",
      date: "12/1/2023",
      progress: 81,
      preview: true,
      previewId: 83385,
    },
    {
      jobId: 59,
      state: "Completed",
      name: "MattisPulvinar.mp3",
      date: "9/20/2023",
      progress: 4,
      preview: false,
      previewId: 83059,
    },
    {
      jobId: 60,
      state: "Failed",
      name: "SapienUrna.jpeg",
      date: "10/26/2023",
      progress: 53,
      preview: false,
      previewId: 41090,
    },
    {
      jobId: 61,
      state: "Completed",
      name: "Sed.xls",
      date: "3/19/2024",
      progress: 94,
      preview: false,
      previewId: 25729,
    },
    {
      jobId: 62,
      state: "Processing",
      name: "Lobortis.jpeg",
      date: "3/11/2024",
      progress: 95,
      preview: false,
      previewId: 24036,
    },
    {
      jobId: 63,
      state: "Failed",
      name: "NullamVariusNulla.mp3",
      date: "8/30/2023",
      progress: 88,
      preview: false,
      previewId: 93280,
    },
    {
      jobId: 64,
      state: "Failed",
      name: "MaecenasTristique.tiff",
      date: "10/10/2023",
      progress: 32,
      preview: false,
      previewId: 84663,
    },
    {
      jobId: 65,
      state: "Processing",
      name: "Suspendisse.xls",
      date: "2/3/2024",
      progress: 16,
      preview: false,
      previewId: 86374,
    },
    {
      jobId: 66,
      state: "Completed",
      name: "LuctusUltriciesEu.xls",
      date: "10/3/2023",
      progress: 97,
      preview: true,
      previewId: 81093,
    },
    {
      jobId: 67,
      state: "Completed",
      name: "MiNullaAc.txt",
      date: "9/20/2023",
      progress: 35,
      preview: false,
      previewId: 8053,
    },
    {
      jobId: 68,
      state: "Failed",
      name: "Condimentum.mp3",
      date: "7/14/2023",
      progress: 68,
      preview: false,
      previewId: 68460,
    },
    {
      jobId: 69,
      state: "Processing",
      name: "VivamusVel.mpeg",
      date: "9/12/2023",
      progress: 97,
      preview: false,
      previewId: 46589,
    },
    {
      jobId: 70,
      state: "Completed",
      name: "PulvinarLobortisEst.gif",
      date: "4/18/2023",
      progress: 1,
      preview: true,
      previewId: 92480,
    },
    {
      jobId: 71,
      state: "Failed",
      name: "Ut.pdf",
      date: "6/11/2023",
      progress: 95,
      preview: false,
      previewId: 75870,
    },
    {
      jobId: 72,
      state: "Processing",
      name: "Sollicitudin.mp3",
      date: "4/11/2023",
      progress: 95,
      preview: true,
      previewId: 49396,
    },
    {
      jobId: 73,
      state: "Completed",
      name: "Pede.avi",
      date: "1/5/2024",
      progress: 67,
      preview: true,
      previewId: 69144,
    },
    {
      jobId: 74,
      state: "Completed",
      name: "Nascetur.mp3",
      date: "8/3/2023",
      progress: 92,
      preview: true,
      previewId: 42899,
    },
    {
      jobId: 75,
      state: "Failed",
      name: "In.xls",
      date: "2/29/2024",
      progress: 31,
      preview: false,
      previewId: 39835,
    },
    {
      jobId: 76,
      state: "Completed",
      name: "OrciVehiculaCondimentum.tiff",
      date: "8/31/2023",
      progress: 31,
      preview: false,
      previewId: 83282,
    },
    {
      jobId: 77,
      state: "Processing",
      name: "Odio.ppt",
      date: "5/3/2023",
      progress: 83,
      preview: false,
      previewId: 49128,
    },
    {
      jobId: 78,
      state: "Processing",
      name: "ArcuSedAugue.txt",
      date: "3/31/2024",
      progress: 7,
      preview: false,
      previewId: 61864,
    },
    {
      jobId: 79,
      state: "Completed",
      name: "Sagittis.mpeg",
      date: "11/5/2023",
      progress: 70,
      preview: false,
      previewId: 5692,
    },
    {
      jobId: 80,
      state: "Completed",
      name: "Ac.tiff",
      date: "2/25/2024",
      progress: 33,
      preview: true,
      previewId: 45288,
    },
    {
      jobId: 81,
      state: "Failed",
      name: "Lacinia.mov",
      date: "12/14/2023",
      progress: 37,
      preview: true,
      previewId: 11843,
    },
    {
      jobId: 82,
      state: "Completed",
      name: "PlateaDictumst.avi",
      date: "3/10/2024",
      progress: 54,
      preview: true,
      previewId: 11087,
    },
    {
      jobId: 83,
      state: "Failed",
      name: "OdioJustoSollicitudin.xls",
      date: "12/2/2023",
      progress: 10,
      preview: false,
      previewId: 33904,
    },
    {
      jobId: 84,
      state: "Completed",
      name: "MorbiAIpsum.txt",
      date: "11/24/2023",
      progress: 7,
      preview: true,
      previewId: 50536,
    },
    {
      jobId: 85,
      state: "Processing",
      name: "IpsumIntegerA.avi",
      date: "4/9/2023",
      progress: 25,
      preview: false,
      previewId: 31488,
    },
    {
      jobId: 86,
      state: "Failed",
      name: "Molestie.gif",
      date: "10/8/2023",
      progress: 52,
      preview: false,
      previewId: 48915,
    },
    {
      jobId: 87,
      state: "Processing",
      name: "Vel.xls",
      date: "3/4/2024",
      progress: 15,
      preview: false,
      previewId: 56064,
    },
    {
      jobId: 88,
      state: "Completed",
      name: "VolutpatErat.mov",
      date: "9/16/2023",
      progress: 1,
      preview: false,
      previewId: 51902,
    },
    {
      jobId: 89,
      state: "Completed",
      name: "OrciNullamMolestie.jpeg",
      date: "6/6/2023",
      progress: 96,
      preview: false,
      previewId: 90838,
    },
    {
      jobId: 90,
      state: "Completed",
      name: "QuisOrciEget.tiff",
      date: "12/10/2023",
      progress: 21,
      preview: false,
      previewId: 21193,
    },
    {
      jobId: 91,
      state: "Processing",
      name: "MaurisNonLigula.ppt",
      date: "9/26/2023",
      progress: 8,
      preview: false,
      previewId: 50742,
    },
    {
      jobId: 92,
      state: "Processing",
      name: "Quam.xls",
      date: "9/2/2023",
      progress: 76,
      preview: true,
      previewId: 27871,
    },
    {
      jobId: 93,
      state: "Processing",
      name: "MaecenasRhoncusAliquam.avi",
      date: "4/29/2023",
      progress: 98,
      preview: true,
      previewId: 10177,
    },
    {
      jobId: 94,
      state: "Completed",
      name: "MaurisViverra.avi",
      date: "6/25/2023",
      progress: 26,
      preview: true,
      previewId: 17132,
    },
    {
      jobId: 95,
      state: "Completed",
      name: "PosuereCubiliaCurae.mp3",
      date: "12/29/2023",
      progress: 26,
      preview: false,
      previewId: 53603,
    },
    {
      jobId: 96,
      state: "Completed",
      name: "NonLigulaPellentesque.xls",
      date: "10/19/2023",
      progress: 35,
      preview: false,
      previewId: 25303,
    },
    {
      jobId: 97,
      state: "Processing",
      name: "Sed.avi",
      date: "1/7/2024",
      progress: 39,
      preview: true,
      previewId: 3365,
    },
    {
      jobId: 98,
      state: "Processing",
      name: "Congue.mpeg",
      date: "8/8/2023",
      progress: 11,
      preview: false,
      previewId: 50137,
    },
    {
      jobId: 99,
      state: "Failed",
      name: "Donec.txt",
      date: "2/26/2024",
      progress: 35,
      preview: false,
      previewId: 73111,
    },
    {
      jobId: 100,
      state: "Processing",
      name: "RisusPraesent.mov",
      date: "8/21/2023",
      progress: 33,
      preview: true,
      previewId: 95179,
    },
  ]




export default RunningJobStates;
