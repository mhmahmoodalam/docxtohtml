import type { Metadata } from "next";
import { Inter } from "next/font/google";
import { ThemeProvider } from "./middlewares";
import { NavigationBar } from "../components/appcomponents";
import "./globals.css";
import { Toaster } from "@/components/ui/sonner";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Create Next App",
  description: "Generated by create next app",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <ThemeProvider
          attribute="class"
          defaultTheme="system"
          enableSystem={false}
          disableTransitionOnChange={false}
        >
          
            <NavigationBar />
            {children}
            <Toaster />
        </ThemeProvider>
      </body>
    </html>
  );
}
