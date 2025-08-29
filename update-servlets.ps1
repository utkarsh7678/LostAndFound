# Script to update Java servlet imports from javax to jakarta

# Define the directory to search for Java files
$javaFiles = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse

# Define the replacements
$replacements = @{
    "javax.servlet" = "jakarta.servlet"
    "javax.servlet.annotation" = "jakarta.servlet.annotation"
    "javax.servlet.http" = "jakarta.servlet.http"
    "javax.servlet.ServletException" = "jakarta.servlet.ServletException"
    "javax.servlet.annotation.WebServlet" = "jakarta.servlet.annotation.WebServlet"
    "javax.servlet.annotation.MultipartConfig" = "jakarta.servlet.annotation.MultipartConfig"
    "javax.servlet.http.HttpServlet" = "jakarta.servlet.http.HttpServlet"
    "javax.servlet.http.HttpServletRequest" = "jakarta.servlet.http.HttpServletRequest"
    "javax.servlet.http.HttpServletResponse" = "jakarta.servlet.http.HttpServletResponse"
    "javax.servlet.http.HttpSession" = "jakarta.servlet.http.HttpSession"
    "javax.servlet.http.Part" = "jakarta.servlet.http.Part"
    "javax.servlet.ServletContext" = "jakarta.servlet.ServletContext"
}

# Process each Java file
foreach ($file in $javaFiles) {
    $content = Get-Content -Path $file.FullName -Raw
    $updated = $false
    
    foreach ($replacement in $replacements.GetEnumerator()) {
        if ($content -match $replacement.Key) {
            $content = $content -replace [regex]::Escape($replacement.Key), $replacement.Value
            $updated = $true
        }
    }
    
    if ($updated) {
        $content | Set-Content -Path $file.FullName -NoNewline
        Write-Host "Updated: $($file.FullName)"
    }
}

Write-Host "Servlet import updates complete!"
