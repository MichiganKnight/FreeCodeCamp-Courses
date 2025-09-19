using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Spreadsheet;

namespace FantasyApp
{
    public class Progam
    {
        public static void Main(string[] args)
        {
            string desktopPath = Environment.GetFolderPath(Environment.SpecialFolder.Desktop);
            string fantasyFolderPath = Path.Combine(desktopPath, "Fantasy Football Analysis");
            string fantasyPath = Path.Combine(fantasyFolderPath, "Fantasy Football Averages 2025.xlsx");

            if (File.Exists(fantasyPath))
            {
                Console.ForegroundColor = ConsoleColor.DarkGreen;
                Console.WriteLine("File Found");
                Console.ResetColor();

                using (SpreadsheetDocument document = SpreadsheetDocument.Open(fantasyPath, false))
                {
                    WorkbookPart? workbookPart = document.WorkbookPart;
                    
                    IEnumerable<Sheet> sheets = workbookPart?.Workbook.Sheets?.OfType<Sheet>() ?? [];
                    foreach (Sheet sheet in sheets)
                    {
                        Console.WriteLine(sheet.Name);
                        
                        if (sheet.Id == null) continue;

                        if (sheet.Id.Value != null)
                        {
                            WorksheetPart worksheetPart = (WorksheetPart)workbookPart?.GetPartById(sheet.Id.Value);
                            SheetData? sheetData = worksheetPart?.Worksheet.GetFirstChild<SheetData>();
                            if (sheetData == null) continue;

                            foreach (Row row in sheetData.Elements<Row>())
                            {
                                IEnumerable<string> cellTexts = row.Elements<Cell>().Select(c => GetCellText(document, c)).ToArray();
                            
                                Console.WriteLine(string.Join("\t", cellTexts));

                                foreach (Cell cell in row.Elements<Cell>())
                                {
                                    if (IsDiv0Error(cell))
                                    {
                                        Console.ForegroundColor = ConsoleColor.DarkRed;
                                        Console.WriteLine($"#DIV/0! Detected in Sheet: \"{sheet.Name}\". Terminating...");
                                        Console.ResetColor();
                                        Console.ReadLine();
                                    
                                        return;
                                    }
                                }
                            }
                        }

                        Console.WriteLine();
                    }

                    static bool IsDiv0Error(Cell cell)
                    {
                        if (cell == null) return false;
                        if (cell.DataType?.Value == CellValues.Error)
                        {
                            string? value = cell.CellValue?.InnerText;
                            
                            return string.Equals(value, "#DIV/0!", StringComparison.OrdinalIgnoreCase);
                        }
                        
                        return false;
                    }
                    
                    static string GetCellText(SpreadsheetDocument document, Cell cell)
                    {
                        if (cell == null) return string.Empty;
                        string value = cell.CellValue?.InnerText ?? string.Empty;

                        if (cell.DataType?.Value == CellValues.SharedString)
                        {
                            if (int.TryParse(value, out int sstIndex))
                            {
                                SharedStringTable? sst = document.WorkbookPart.SharedStringTablePart?.SharedStringTable;
                                return sst?.ElementAtOrDefault(sstIndex)?.InnerText ?? string.Empty;   
                            }

                            return string.Empty;
                        }
                        
                        if (cell.DataType?.Value == CellValues.InlineString)
                        {
                            return cell.InlineString?.Text?.Text ?? string.Empty;
                        }
                        
                        if (cell.DataType?.Value == CellValues.Boolean)
                        {
                            return value == "1" ? "True" : "False";
                        }
                        
                        return value;
                    }

                    static Cell? GetCell(Row row, string columnName)
                    {
                        return row.Elements<Cell>()
                            .FirstOrDefault(c =>
                            {
                                string? r = c.CellReference?.Value;
                                
                                if (string.IsNullOrEmpty(r)) return false;

                                int i = 0;
                                while (i < r.Length && char.IsLetter(r[i])) i++;
                                
                                string col = r[..i];

                                return string.Equals(col, columnName, StringComparison.OrdinalIgnoreCase);
                            });   
                    }
                }
                
                Console.ReadLine();
            }
            else
            {
                Console.ForegroundColor = ConsoleColor.DarkRed;
                Console.WriteLine("File Not Found");
                Console.ResetColor();
            }
        }
    }
}